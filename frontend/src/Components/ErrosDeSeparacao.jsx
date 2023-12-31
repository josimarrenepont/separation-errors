/* eslint-disable react-hooks/exhaustive-deps */
import axios from 'axios';
import React, { useState } from 'react';
import "./DateRangePicker";
import "./ErrosDeSeparacao";
import MeuBotao from './MeuBotao';
import SeparationForm from './SeparationForm';

function ErrosDeSeparacao() {
  const [showSeparationForm, setShowSeparationForm] = useState(false);
  const [errors, setErrors] = useState([]);
  const [codProduct, setCodProduct] = useState('');
  const [loadingByProduct, setLoadingByProduct] = useState(false);


  const handleButtonClick = () => {
    setShowSeparationForm(true);
  };

  
  const fetchErrorsByProductCode = async () => {
    try {
      if (!codProduct) {
        console.error('Código do produto vazio');
        return;
      }
      setLoadingByProduct(true);
      const response = await axios.get(`http://localhost:8080/separation-error-history/error-history/${codProduct}`);
      const errors = response.data;
      setErrors(errors);
    } catch (error) {
      console.error('Erro ao buscar erros de separação por código do produto:', error);
    } finally {
      setLoadingByProduct(false);
    }
  };
  

  return (
    <div>
      <h1>Inserir Erros de Separação</h1>
      <MeuBotao onClick={handleButtonClick} />
      {showSeparationForm && <SeparationForm/>}

      <h2>Lista de Erros de Separação:</h2>
      <table>
        <thead>
          <tr>
            <th>Nome</th>
            <th>Peças Mais</th>
            <th>Peças Menos</th>
            <th>Peças Erradas</th>
            <th> Data </th>
          </tr>
        </thead>
        <tbody>
          {errors.map((error) => (
            <tr key={error.id}>
              <td>{error.name}</td>
              <td>{error.errorPcMais}</td>
              <td>{error.errorPcMenos}</td>
              <td>{error.errorPcErrada}</td>
              <td>{error.date}</td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Campo e botão para buscar por código do produto */}
      <label htmlFor="codProduct">Código do Produto:</label>
      <input
        type="number"
        id="codProduct"
        value={codProduct}
        onChange={(e) => setCodProduct(e.target.value)}
        name="codProduct"
        required
      />
      <button type="button" onClick={fetchErrorsByProductCode}>
        Buscar Erros por Código do Produto
      </button>
      {loadingByProduct && <p>Buscando erros por código do produto...</p>}

    </div>
  );
  
}

export default ErrosDeSeparacao;
