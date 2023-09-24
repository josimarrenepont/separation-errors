import axios from 'axios';
import React, { useState } from 'react';
import './ChartComponent';
import './DateRangePicker';
import './ErrosDeSeparacao';
import './MeuBotao';
import './SeparationForm';

function SeparationForm() {
  const [data, setData] = useState('');
  const [employee, setEmployee] = useState('');
  const [separation, setSeparation] = useState('');
  const [palette, setPalette] = useState('');
  const [produto, setProduto] = useState('');
  const [error, setError] = useState(null);

  const handleSubmit = (e) => {
    e.preventDefault();

    const formData = {
      data,
      employee,
      separation,
      palette,
      produto,
    };

    axios.post('http://localhost:8080/separations', formData)

    
      .then((response) => {
        console.log('Separação salva com sucesso:', response.data);
        setData('');
        setEmployee('');
        setSeparation('');
        setPalette('');
        setProduto('');
      })
      .catch((error) => {
        console.error('Erro ao salvar separação:', error);
        setError('Erro ao salvar separação. Por favor, tente novamente.'); // Define a mensagem de erro
      });
  };

  return (
    <div className="container">
      <h1>Formulário de Separação</h1>
      <div className='SeparationForm'>
      {error && <div className="error-message">{error}</div>} {/* Exibe a mensagem de erro se houver */}
        <form onSubmit={handleSubmit}>
          <label htmlFor="data">Data:</label>
          <input
            type="date"
            id="data"
            value={data}
            onChange={(e) => setData(e.target.value)}
            name="data"
            required
          /><br /><br />

          <label htmlFor="employee">Funcionário:</label>
          <select id="employee" name="employee" required>
            <option value=""></option>
            <option value="Alexandre">Alexandre</option>
            <option value="Denison">Denison</option>
            <option value="Gilvan">Gilvan</option>
            <option value="Gildoberto">Gildoberto</option>
          </select><br /><br />

          <label htmlFor="separation">Separação:</label>
          <select id="separation" name="separation" required>
            <option value=""></option>
            <option value="pcMais">pcMais</option>
            <option value="pcMenos">pcMenos</option>
            <option value="pcErrada">pcErrada</option>
          </select><br /><br />

          <label htmlFor="error">Erro:</label>
          <input type="text" id="error" name="error" required /><br /><br />

          <label htmlFor="palette">Palete:</label>
          <input type="text" id="palette" name="palette" required /><br /><br />

          <label htmlFor="produto">Produto:</label>
          <input type="text" id="produto" name="produto" required /><br /><br />

         

          <input type="submit" value="Salvar" />
    
        </form>
      </div>
    </div>
  );
}

export default SeparationForm;
