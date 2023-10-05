/* eslint-disable @typescript-eslint/no-unused-vars */
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import './ErrosDeSeparacao';
import MeuBotao from './MeuBotao';
import SeparationForm from './SeparationForm';

function ErrosDeSeparacao() {
  const [showSeparationForm, setShowSeparationForm] = useState(false);
  const [errors, setErrors] = useState([]);
  const [startDate] = useState(''); // Data mínima escolhida pelo usuário
  const [endDate] = useState(''); // Data máxima escolhida pelo usuário

  const handleButtonClick = () => {
    setShowSeparationForm(true);
  };

  // Função para buscar os erros de separação no banco de dados com base nas datas
  const fetchErrors = () => {
    axios
      .get('http://localhost:8080/separations', {
        params: { startDate, endDate }, // Passa as datas como parâmetros da solicitação GET
      })
      .then((response) => {
        setErrors(response.data);
      })
      .catch((error) => {
        console.error('Erro ao buscar erros de separação:', error);
      });
  };

  useEffect(() => {
    // Chamando a função para buscar os erros de separação quando o componente é montado
    fetchErrors();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [startDate, endDate]); // Certifica-se de que a função é chamada quando as datas são atualizadas

  return (
    <div>
      <h1>Inserir Erros de Separação</h1>
      <MeuBotao onClick={handleButtonClick} />
      {showSeparationForm && <SeparationForm />}

      {/* Aqui você pode adicionar um seletor de datas para o usuário escolher a data mínima e máxima */}

      <h2>Lista de Erros de Separação:</h2>
      <ul>
        {errors.map((error) => (
          <li key={error.id}>
            {error.name}: TotPcMais= {error.subTotPcMais}, TotPcMenos= {error.subTotPcMenos}, TotPcErrada= {error.subTotPcErrada}
            <br />
            {error.errorMessage} - Ano: {new Date(error.date).getFullYear()}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ErrosDeSeparacao;