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
  const [errorPcMais, setErrorPcMais] = useState('');
  const [errorPcMenos, setErrorPcMenos] = useState('');
  const [errorPcErrada, setErrorPcErrada] = useState('');
  const [setSuccessMessage] = useState(''); // Estado para feedback de sucesso
  const [setFormData] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Primeiro, busque o ID do funcionário com base no nome selecionado
    try {
      const response = await axios.get(`http://localhost:8080/employees/findByName/${employee.id}`);
      const employeeData = response.data; // O response.data é um objeto que representa o funcionário
    
      // Agora, você deve acessar o employeeId do objeto retornado
      const employee = employeeData.id;

      // Continue com o envio da solicitação POST com o ID do funcionário
      const errorData = {
        pcMais: parseInt(pcMais, 10),
        pcMenos: parseInt(pcMenos, 10),
        pcErrada: parseInt(pcErrada, 10),
        errorPcMais: parseInt(errorPcMais, 10),
        errorPcMenos: parseInt(errorPcMenos, 10),
        errorPcErrada: parseInt(errorPcErrada, 10)
      };

      const formData = {
        date,
        employee: employee.id, // Use o ID do funcionário em vez do nome
        separation: parseInt(separation, 10),
        pallet: parseInt(pallet, 10),
        codProduct: parseInt(codProduct, 10),
        errorPcMais: errorData,
        errorPcMenos: errorData,
        errorPcErrada: errorData
      };

      // Envie a solicitação POST com o ID do funcionário
      const postResponse = await axios.post('http://localhost:8080/separations', formData);

      console.log('Erros atualizados com sucesso:', postResponse.data);
          // Defina a mensagem de sucesso e limpe os campos
          setSuccessMessage('Erros atualizados com sucesso');
          setFormData({
            date: '',
            employee: '',
            separation: '',
            pallet: '',
            codProduct: '',
            pcMais: '',
            pcMenos: '',
            pcErrada: '',
            errorPcMais: '',
            errorPcMenos: '',
            errorPcErrada: '',
          });
    } catch (error) {
      console.error('Erro ao buscar o ID do funcionário:', error);
      ('Erro ao atualizar erros. Por favor, tente novamente.');
    }
  };

  return (
    <div className="container">
      <h1>Formulário de Separação</h1>
      <div className='SeparationForm'>
        {errorPcMais && <div className="error-message">{errorPcMais}</div>}
        {errorPcMenos && <div className="error-message">{errorPcMenos}</div>}
        {errorPcErrada && <div className="error-message">{errorPcErrada}</div>}
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
            value={employee.name}
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
            type="text"
            id="separation"
            value={separation}
            onChange={(e) => setSeparation(e.target.value)}
            name="separation"
            required
          /><br /><br />
          <label htmlFor="pallet">Pallet:</label>
          <input
            type="text"
            id="pallet"
            value={pallet}
            onChange={(e) => setPallet(e.target.value)}
            name="pallet"
            required
          /><br /><br />
          <label htmlFor="codProduct">codProduct:</label>
          <input
            type="text"
            id="codProduct"
            value={codProduct}
            onChange={(e) => setCodProduct(e.target.value)}
            name="codProduct"
            required
          /><br /><br />
          <label htmlFor="errorPcMais">errorPcMais:</label>
          <input
            type="text"
            id="errorPcMais"
            value={errorPcMais}
            onChange={(e) => setErrorPcMais(e.target.value)}
            name="errorPcMais"
            required
          /><br /><br />
          <label htmlFor="errorPcMenos">errorPcMenos:</label>
          <input
            type="text"
            id="errorPcMenos"
            value={errorPcMenos}
            onChange={(e) => setErrorPcMenos(e.target.value)}
            name="errorPcMenos"
            required
          /><br /><br />
          <label htmlFor="errorPcErrada">errorPcErrada:</label>
          <input
            type="text"
            id="errorPcErrada"
            value={errorPcErrada}
            onChange={(e) => setErrorPcErrada(e.target.value)}
            name="errorPcErrada"
            required
          /><br /><br />
          <label htmlFor="pcMais">PC Mais:</label>
          <select
            id="pcMais"
            name="pcMais"
            value={pcMais}
            onChange={(e) => setPcMais(e.target.value)}
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
          </select><br /><br />
          <label htmlFor="pcMenos">PC Menos:</label>
          <select
            id="pcMenos"
            name="pcMenos"
            value={pcMenos}
            onChange={(e) => setPcMenos(e.target.value)}
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
          </select><br /><br />
          <label htmlFor="pcErrada">PC Errada:</label>
          <select
            id="pcErrada"
            name="pcErrada"
            value={pcErrada}
            onChange={(e) => setPcErrada(e.target.value)}
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
          </select><br /><br />
          <input type="submit" value="Salvar" />
        </form>
      </div>
    </div>
  );
}

export default SeparationForm;
