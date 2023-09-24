import axios from 'axios';
import React, { useEffect, useState } from 'react';
import MeuBotao from './MeuBotao';
import SeparationForm from './SeparationForm';


function ErrosDeSeparacao() {
  const [showSeparationForm, setShowSeparationForm] = useState(false);
  const [separations, setSeparations] = useState([]);

  const handleButtonClick = () => {
    setShowSeparationForm(true);
  };

  useEffect(() => {
    axios.get('http://localhost:8080/separations') // Use o método GET para buscar separações
    .then((response) => {
      setSeparations(response.data);
    })
    .catch((error) => {
      console.error('Erro ao buscar separações:', error);
    });
}, []);


  return (
    <div>
      <h1>Inserir Erros de Separação</h1>
      <MeuBotao onClick={handleButtonClick} />
      {showSeparationForm && <SeparationForm />}
      
      <h2>Lista de Separações:</h2>
      <ul>
        {separations.map((separation) => (
          <li key={separation.id}>{separation.description}</li>
        ))}
      </ul>
    </div>
  );
}

export default ErrosDeSeparacao;
