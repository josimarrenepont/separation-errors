import axios from 'axios';
import React, { useState } from 'react';


function SeparationForm() {
  const [date, setDate] = useState('');
  const [employee, setEmployee] = useState('');
  const [pallet, setPallet] = useState('');
  const [codProduct, setCodProduct] = useState('');
  const [pcMais, setPcMais] = useState('');
  const [pcMenos, setPcMenos] = useState('');
  const [pcErrada, setPcErrada] = useState('');
  const [errorPcMais, setErrorPcMais] = useState('');
  const [errorPcMenos, setErrorPcMenos] = useState('');
  const [errorPcErrada, setErrorPcErrada] = useState('');
  const [setSuccessMessage] = useState(''); // Estado para feedback de sucesso

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Primeiro, busque o funcionário com base no nome selecionado
    try {
      const response = await axios.get(`http://localhost:8080/employees/findByName/${employee}`);
      const employeeData = response.data;

      if (employeeData) {
        // O funcionário existe, agora você pode atualizar os erros
        const errorData = {
          pcMais: parseInt(pcMais, 10),
          pcMenos: parseInt(pcMenos, 10),
          pcErrada: parseInt(pcErrada, 10),
          errorPcMais: parseInt(errorPcMais, 10),
          errorPcMenos: parseInt(errorPcMenos, 10),
          errorPcErrada: parseInt(errorPcErrada, 10),
        };

        const updatedSeparation = {
          ...employeeData,
          errorPcMais: (employeeData.errorPcMais || 0) + errorData.pcMais,
          errorPcMenos: (employeeData.errorPcMenos || 0) + errorData.pcMenos,
          errorPcErrada: (employeeData.errorPcErrada || 0) + errorData.pcErrada,
        };

        // Faça uma solicitação para atualizar o funcionário com os novos erros
        const updateResponse = await axios.put(`http://localhost:8080/separations/${employeeData.id}`, updatedSeparation, {
          headers: {
            'Content-Type': 'application/json',
          },
        });
        console.log('Erros atualizados com sucesso no funcionário:', updateResponse.data);
        setSuccessMessage('Erros atualizados com sucesso no funcionário');
      } else {
        console.error('Funcionário não encontrado');
      }

      // Limpe os campos do formulário
      setEmployee('');
      setDate('');
      setPallet('');
      setCodProduct('');
      setPcMais('');
      setPcMenos('');
      setPcErrada('');
      setErrorPcMais('');
      setErrorPcMenos('');
      setErrorPcErrada('');
    } catch (error) {
      console.error('Erro ao buscar o funcionário ou atualizar os erros:', error);
    }
  };

  return (
    <div className="container">
      <h1>Formulário de Separação</h1>
      <div className="SeparationForm">
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
          />
          <br /><br />

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
          </select>
          <br /><br />

          <label htmlFor="pallet">Pallet:</label>
          <input
            type="number"
            id="pallet"
            value={pallet}
            onChange={(e) => setPallet(e.target.value)}
            name="pallet"
            required
          />
          <br /><br />

          <label htmlFor="codProduct">codProduct:</label>
          <input
            type="number"
            id="codProduct"
            value={codProduct}
            onChange={(e) => setCodProduct(e.target.value)}
            name="codProduct"
            required
          />
          <br /><br />

          <label htmlFor="errorPcMais">errorPcMais:</label>
          <input
            type="number"
            id="errorPcMais"
            value={errorPcMais}
            onChange={(e) => setErrorPcMais(e.target.value)}
            name="errorPcMais"
            required
          />
          <br /><br />

          <label htmlFor="errorPcMenos">errorPcMenos:</label>
          <input
            type="number"
            id="errorPcMenos"
            value={errorPcMenos}
            onChange={(e) => setErrorPcMenos(e.target.value)}
            name="errorPcMenos"
            required
          />
          <br /><br />

          <label htmlFor="errorPcErrada">errorPcErrada:</label>
          <input
            type="number"
            id="errorPcErrada"
            value={errorPcErrada}
            onChange={(e) => setErrorPcErrada(e.target.value)}
            name="errorPcErrada"
            required
          />
          <br /><br />

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
          </select>
          <br /><br />

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
          </select>
          <br /><br />

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
          </select>
          <br /><br />

          <input type="submit" value="Salvar" />
        </form>
      </div>
    </div>
  );
}

export default SeparationForm;
