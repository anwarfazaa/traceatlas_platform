// Create an object to store chart instances
const charts = {};

// Function to create a new chart
function createChart(chartId, chartType) {
  const canvas = document.getElementById(chartId);
  const chart = new Chart(canvas, {
    type: chartType,
    data: {
      labels: [],
      datasets: [
        {
          data: [],
          borderColor: 'blue',
          fill: false
        }
      ]
    }
  });

  // Store the chart instance in the object using the chartId as the key
  charts[chartId] = chart;
}

// Function to add data to a specific chart
function addDataToChart(chartId, label, newData) {
  // Retrieve the chart instance from the object
  const chart = charts[chartId];

  // Check if the chart exists
  if (chart) {
    // Add data to the specific chart
    chart.data.labels.push(label);
    chart.data.datasets[0].data.push(newData);
    chart.update();
  } else {
    console.error('Chart not found:', chartId);
  }
}

// Example usage
createChart('areaChart', 'line');
createChart('chart2', 'line', ['Red', 'Blue', 'Yellow'], [50, 75, 100]);

addDataToChart('chart1', 'April', 300);
addDataToChart('chart2', 'Green', 80);
