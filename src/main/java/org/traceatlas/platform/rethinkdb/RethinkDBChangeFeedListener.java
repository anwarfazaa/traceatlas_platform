package org.traceatlas.platform.rethinkdb;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Result;
import org.springframework.stereotype.Service;
import org.traceatlas.platform.configuration.RethinkDbConfiguration;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RethinkDBChangeFeedListener {

    private final RethinkDB r = RethinkDB.r;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public void startListening() {
        for (int i = 0; i < 5; i++) {
            executorService.submit(this::listenChangeFeed);
        }
    }

    private void listenChangeFeed() {
        RethinkDbConfiguration rDbConfig = new RethinkDbConfiguration();
        try (Connection conn = r.connection().hostname(rDbConfig.getHost()).port(rDbConfig.getPort()).connect()) {
            Result<Object> cursor = r.db("rtDashboard").table("dbTable_rt")
                    .changes().run(conn);
            cursor.forEach(change -> {
                System.out.println("Change at document: " + change);
                // To be implemented
                // adding Kafka handlers
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopListening() {
        executorService.shutdown();
    }

}
