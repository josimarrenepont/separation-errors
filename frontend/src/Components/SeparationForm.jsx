
import axios from 'axios';
import React, { useState } from 'react';
import './ChartComponent';
import './DateRangePicker';
import './ErrosDeSeparacao';
import './MeuBotao';
import './SeparationForm';

function SeparationForm() {
  const [date, setDate] = useState('');
  const [employee, setEmployee] = useState('');
  const [separation, setSeparation] = useState('');
  const [pallet, setPallet] = useState('');
  const [codProduct, setCodProduct] = useState('');
  const [pcMais, setPcMais] = useState('');
  const [pcMenos, setPcMenos] = useState('');
  const [pcErrada, setPcErrada] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Primeiro, busque o ID do funcionário com base no nome selecionado
    try {
      const response = await axios.get(`http://localhost:8080/employees/findByName/${employee}`);
      const employeeId = response.data;

      // Continue com o envio da solicitação POST com o ID do funcionário
      const errorData = {
        pcMais: pcMais,
        pcMenos: pcMenos,
        pcErrada: pcErrada,
      };

      const formData = {
        date,
        employee: employeeId, // Use o ID do funcionário em vez do nome
        separation,
        pallet,
        codProduct,
        error: errorData,
      };

      // Envie a solicitação POST com o ID do funcionário
      const postResponse = await axios.post('http://localhost:8080', formData);

      console.log('Erros atualizados com sucesso:', postResponse.data);
      // Resto do código para limpar os campos e lidar com sucesso
    } catch (error) {
      console.error('Erro ao buscar o ID do funcionário:', error);
      setError('Erro ao atualizar erros. Por favor, tente novamente.');
    }
  };

  return (
    <div className="container">
      <h1>Formulário de Separação</h1>
      <div className='SeparationForm'>
        {error && <div className="error-message">{error}</div>}
        <form onSubmit={handleSubmit}>
          <label htmlFor="date">Date:</label>
        
        <input
        
        type="date"
        
        id="date"
        
        value={date}
        
        onChange={(e) => setDate(e.target.value)}
        
        name="date"
        
        required
        
        /><br /><br />
        
        <label htmlFor="employee">Funcionário:</label>
        
        <select
        
        id="employee"
        
        name="employee"
        
        value={employee}
        
        onChange={(e) => setEmployee(e.target.value)}
        
        required
        
        >
        
        <option value=""></option>
        
        <option value="Eduardo">Eduardo</option>
        
        <option value="Josenilson">Josenilson</option>
        
        <option value="Gilvan">Gilvan</option>
        
        <option value="Jose">Jose</option>
        
        <option value="Gildoberto">Gildoberto</option>

        </select><br /><br />
        
        <label htmlFor="separation">Separação:</label>
<input
  type="number" 
  id="separation"
  value={separation}
  onChange={(e) => setSeparation(e.target.value)} 
  name="separation"
  required
/><br /><br />

<label htmlFor="pallet">Pallet:</label>
<input
  type="number"
  id="pallet"
  value={pallet}
  onChange={(e) => setPallet(e.target.value)}
  name="pallet"
  required
/><br /><br />

<label htmlFor="codProduct">codProduct:</label>
<input
  type="number" 
  id="codProduct"
  value={codProduct}
  onChange={(e) => setCodProduct(e.target.value)}
  name="codProduct"
  required
/><br /><br />

        
        <label htmlFor="pcMais">PC Mais:</label>
        
        <select
        
        id="pcMais"
        
        name="pcMais"
        
        value={pcMais}
        
        onChange={(e) => setPcMais(Number(e.target.value))}
        
        required
        
        >
        
        <option value="0">0</option>
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
        <option value="6">6</option>
        <option value="7">7</option>
        <option value="8">8</option>
        <option value="9">9</option>
        <option value="10">10</option>
        
        {/* Adicione mais opções conforme necessário */}
        
        </select><br /><br />
        
        <label htmlFor="pcMenos">PC Menos:</label>
        
        <select
        
        id="pcMenos"
        
        name="pcMenos"
        
        value={pcMenos}
        
        onChange={(e) => setPcMenos(Number(e.target.value))}
        
        required
        
        >
        
        <option value="0">0</option>
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
        <option value="6">6</option>
        <option value="7">7</option>
        <option value="8">8</option>
        <option value="9">9</option>
        <option value="10">10</option>
        
        {/* Adicione mais opções conforme necessário */}
        
        </select><br /><br />
        
        <label htmlFor="pcErrada">PC Errada:</label>
        
        <select
        
        id="pcErrada"
        
        name="pcErrada"
        
        value={pcErrada}
        
        onChange={(e) => setPcErrada(Number(e.target.value))}
        
        required
        
        >
        
        <option value="0">0</option>
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
        <option value="6">6</option>
        <option value="7">7</option>
        <option value="8">8</option>
        <option value="9">9</option>
        <option value="10">10</option>
        
        {/* Adicione mais opções conforme necessário */}
        
        </select><br /><br />
        
        <input type="submit" value="Salvar" />
        </form>
      </div>
    </div>
  );
}

export default SeparationForm;

			
        
