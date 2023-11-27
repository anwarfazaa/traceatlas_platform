var socket = new SockJS('/ws');
var stompClient = Stomp.over(socket);

var tableBody = document.querySelector('#messageTable tbody');
var chartCanvas = document.querySelector('#areaChart');

const charts = {};

function createChart(canvasId,labelValue) {
    const chart = new Chart(canvasId, {
            type: 'line',
            data: {
                labels: [],
                datasets: [{
                    label: labelValue,
                    data: [],
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                    fill: false,
                    lineTension: 0
                }],
                options: {
                    animation: false,
                    scales: {
                        xAxes: [{
                            type: 'realtime',
                            realtime: {
                                delay: 2000,
                                refresh: 1000
                            }
                        }],
                        yAxes: [{
                            ticks: {
                                suggestedMin: 50,
                                suggestedMax: 100
                            }
                        }]
                    }
                }
            },
    });
    charts[canvasId] = chart;
}

    createChart('areaChart','CPU Utilization %');
    createChart('barChart','Random Numbers From Script');

    // Function to add data to the chart
    function addData(label, data , canvasId) {
        console.log(canvasId);
        charts[canvasId].data.labels.push(label);
        charts[canvasId].data.datasets.forEach((dataset) => {
            dataset.data.push(data);
        });
        if(charts[canvasId].data.labels.length > 20) { // if more than 20 data points, remove the first one
            charts[canvasId].data.labels.shift();
            charts[canvasId].data.datasets.forEach((dataset) => {
                dataset.data.shift();
            });
        }
        charts[canvasId].update();
    }

    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/message', function(response) {
            var jsonObject = JSON.parse(response.body);
            // Add a row to the table
            // var row = tableBody.insertRow();
            // var cell = row.insertCell();
            // cell.textContent = message;
            console.log(jsonObject.metric_value);
            // Add data to the chart
            var timestamp = new Date().toLocaleTimeString(); // Use the current time as the x-axis label
            addData(timestamp, jsonObject.metric_value , jsonObject.script_name);
        });
    });
