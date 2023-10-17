/* eslint-disable react-refresh/only-export-components */
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { createRoot } from 'react-dom/client';
import './App.css';
import DateRangePicker from './components/DateRangePicker';
import ErrosDeSeparacao from './components/ErrosDeSeparacao';
import './index.css';

// ...

// ...

// ...

function App() {
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [errorSummary, setErrorSummary] = useState({});
  const [loading, setLoading] = useState(false);

  const handleDateRangeChange = (start, end) => {
    setStartDate(start);
    setEndDate(end);
  };

  useEffect(() => {
    if (startDate && endDate && startDate <= endDate) {
      fetchErrorsInRange(startDate, endDate);
    } else {
      // Caso contrário, limpe a lista de erros
      setErrorSummary({});
    }
  }, [startDate, endDate]);

  const fetchErrorsInRange = async (start, end) => {
    setLoading(true);

    try {
      // Faça uma solicitação GET ao servidor para buscar erros no intervalo de datas
      const response = await axios.get('http://localhost:8080/separations', {
        params: { startDate: start, endDate: end },
      });

      // Verifique se a resposta foi bem-sucedida
      if (response.status === 200) {
        // Filtrar erros que estão dentro do intervalo de datas
        const filteredErrors = response.data.filter((error) => {
          const errorDate = new Date(error.date);
          return errorDate >= new Date(startDate) && errorDate <= new Date(endDate);
        });

        // Calcular a soma dos erros para cada funcionário
        const summary = calculateErrorSummary(filteredErrors);

        // Atualize o resumo de erros com os dados calculados
        setErrorSummary(summary);
      } else {
        console.error('Erro ao buscar erros:', response.statusText);
      }
    } catch (error) {
      console.error('Erro ao buscar erros:', error);
    } finally {
      setLoading(false);
    }
  };

  // Função para calcular o resumo de erros
  const calculateErrorSummary = (filteredErrors) => {
    const summary = {};
    filteredErrors.forEach((error) => {
      if (!summary[error.name]) {
        summary[error.name] = 0;
      }
      summary[error.name] += 1; // Incrementa o contador de erros para o funcionário
    });
    return summary;
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
        {loading && <p>Carregando...</p>}
        {!loading && Object.keys(errorSummary).length > 0 ? (
          <table>
            <thead>
              <tr>
                <th>Funcionário</th>
                <th>Total de Erros</th>
              </tr>
            </thead>
            <tbody>
              {Object.keys(errorSummary).map((employeeName) => (
                <tr key={employeeName}>
                  <td>{employeeName}</td>
                  <td>{errorSummary[employeeName]}</td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : !loading && Object.keys(errorSummary).length === 0 ? (
          <p>Não há erros nas datas selecionadas.</p>
        ) : null}
      </div>

      {/* Segunda visualização */}
      <div>
        <ErrosDeSeparacao />
      </div>
    </div>
  );
}

createRoot(document.getElementById('root')).render(<App />);
