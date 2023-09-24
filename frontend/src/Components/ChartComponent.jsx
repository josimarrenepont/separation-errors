// ChartComponent.js

import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Bar } from 'react-chartjs-2';

function ChartComponent() {
  const [chartData, setChartData] = useState({});

  useEffect(() => {
    axios.get('/api/chart-data')
      .then((response) => {
        const data = response.data;
        // Transforme os dados em um formato adequado para o gráfico, por exemplo:
        const chartData = {
          labels: data.map((item) => item.label),
          datasets: [
            {
              label: 'Valores',
              data: data.map((item) => item.value),
              backgroundColor: 'rgba(75, 192, 192, 0.6)', // Cor das barras
              borderColor: 'rgba(75, 192, 192, 1)',
              borderWidth: 1,
            },
          ],
        };
        setChartData(chartData);
      })
      .catch((error) => {
        console.error('Erro ao buscar dados do gráfico:', error);
      });
  }, []);

  return (
    <div>
      <h2>Gráfico de Barras</h2>
      <Bar data={chartData} />
    </div>
  );
}

export default ChartComponent;
