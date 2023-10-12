import axios from 'axios';
import React, { useState } from 'react';
import { createRoot } from 'react-dom/client';
import './App.css';
import DateRangePicker from './components/DateRangePicker';
import ErrosDeSeparacao from './components/ErrosDeSeparacao';
import './index.css';


// eslint-disable-next-line react-refresh/only-export-components
function App() {
  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();
  const [errors, setErrors] = useState([]);

  const handleDateRangeChange = (start, end) => {
    setStartDate(start);
    setEndDate(end);
    // Atualize os erros com base nas datas escolhidas pelo usuário
    fetchErrorsInRange(start, end);
  };

  const fetchErrorsInRange = async (start, end) => {
    try {
      // Faça uma solicitação GET ao servidor para buscar erros no intervalo de datas
      const response = await axios.get('http://localhost:8080/separations', {
        params: { startDate: start, endDate: end },
      });
      

      // Verifique se a resposta foi bem-sucedida
      if (response.status === 200) {
        // Atualize a lista de erros com os dados recebidos do servidor
        setErrors(response.data);
      } else {
        console.error('Erro ao buscar erros:', response.statusText);
      }
    } catch (error) {
      console.error('Erro ao buscar erros:', error);
    }
  };

  return (
    <div>
        <header>
    <h1>Erros de Separação</h1>
    <DateRangePicker onDateRangeChange={handleDateRangeChange} />
  </header>
  {/* Primeira visualização */}
  <div>
    <h2>Erros no período selecionado ({startDate} a {endDate})</h2>
    <table>
      <thead>
        <tr>
          <th>Nome</th>
          <th>Total de Erros</th>
        </tr>
      </thead>
      <tbody>
        {errors.map((error) => (
          <tr key={error.id}>   
            <td>{error.name}</td>
            <td>{error.totalErrors}{errors.map.length}</td>
          </tr>
        ))}
      </tbody>
    </table>
  </div>

      {/* Segunda visualização */}
      <div>
        <ErrosDeSeparacao />
      </div>
    </div>
  );
}

createRoot(document.getElementById('root')).render(<App />);

