/* eslint-disable @typescript-eslint/no-unused-vars */
import axios from 'axios';
import React, { useState } from 'react';

function SeparationForm() {
  const [date, setDate] = useState('date.Date');
  const [employee, setEmployee] = useState('employee.name');
  const [pallet, setPallet] = useState('pallet.parseInt');
  const [codProduct, setCodProduct] = useState('codProduct.parseInt');
  const [pcMais, setPcMais] = useState('pcMais.parseInt');
  const [pcMenos, setPcMenos] = useState('pcMenos.parseInt');
  const [pcErrada, setPcErrada] = useState('pcErrada.parseInt');
  const [errorPcMais, setErrorPcMais] = useState('errorPcMais.parseInt');
  const [errorPcMenos, setErrorPcMenos] = useState('errorPcMenos.parseInt');
  const [errorPcErrada, setErrorPcErrada] = useState('errorPcErrada.parseInt');
  const [successMessage, setSuccessMessage] = useState('');
  const [errorHistory, setErrorHistory] = useState([]);
  

  const parseNumberIfNotEmpty = (value) => {
    return value.trim() !== '' ? parseInt(value, 10) : null;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.get(`http://localhost:8080/employees/findByName/${employee}`);
      const employeeData = response.data;

      if (employeeData) {
        const errorData = {
          date: (new Date().getTime()),
          name: employee,
          codProduct: parseInt(codProduct, 10),
          pallet: parseInt(pallet, 10),
          pcMais: parseInt(pcMais, 10),
          pcMenos: parseInt(pcMenos, 10),
          pcErrada: parseInt(pcErrada, 10),
          errorPcMais: parseInt(errorPcMais, 10),
          errorPcMenos: parseInt(errorPcMenos, 10),
          errorPcErrada: parseInt(errorPcErrada, 10),
          
        };

        const updatedSeparation = {
          
            ...employeeData,
                date: (new Date().getTime()),
                employeeData,
                codProduct,
                pallet,
                pcMais: (employeeData.pcMais || 0) + errorData.pcMais,
                pcMenos: (employeeData.pcMenos || 0) + errorData.pcMenos,
                pcErrada: (employeeData.pcErrada || 0) + errorData.pcErrada,
                errorPcMais: (employeeData.errorPcMais || 0) + errorData.errorPcMais,
                errorPcMenos: (employeeData.errorPcMenos || 0) + errorData.errorPcMenos,
                errorPcErrada: (employeeData.errorPcErrada || 0) + errorData.errorPcErrada,
                subTotPcMais: (employeeData.subTotPcMais || 0) + errorData.errorPcMais + errorData.pcMais,
                subTotPcMenos: (employeeData.subTotPcMenos || 0) + errorData.errorPcMenos + errorData.pcMenos,
                subTotPcErrada: (employeeData.subTotPcErrada || 0) + errorData.errorPcErrada + errorData.pcErrada,
                errorHistory: [...errorHistory, errorData]
        };

        const updateResponse = await axios.put('http://localhost:8080/separations', updatedSeparation, {
          headers: {
            'Content-Type': 'application/json',
          },
        });

        console.log('Erros atualizados com sucesso no funcionário:', updateResponse.data);
        setSuccessMessage('Erros atualizados com sucesso no funcionário');
      } else {
        console.error('Funcionário não encontrado');
      }

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
      setErrorHistory([]);
      } catch (error) {
      console.error('Erro ao buscar o funcionário ou atualizar os erros:', error);
    }
  };

  return (
    <div className="container">
      <h1>Formulário de Separação</h1>
      
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
            
          />
          <br /><br />

          <label htmlFor="errorPcMenos">errorPcMenos:</label>
          <input
            type="number"
            id="errorPcMenos"
            value={errorPcMenos}
            onChange={(e) => setErrorPcMenos(e.target.value)}
            name="errorPcMenos"
            
          />
          <br /><br />

          <label htmlFor="errorPcErrada">errorPcErrada:</label>
          <input
            type="number"
            id="errorPcErrada"
            value={errorPcErrada}
            onChange={(e) => setErrorPcErrada(e.target.value)}
            name="errorPcErrada"
            
          />
          <br /><br />

          <label htmlFor="pcMais">PC Mais:</label>
          <input
            type="number"
            id="pcMais"
            value={pcMais}
            onChange={(e) => setPcMais(e.target.value)}
            name="pcMais" 
          />
          <br /><br />

          <label htmlFor="pcMenos">PC Menos:</label>
          <input
            type="number"
            id="pcMenos"
            value={pcMenos}
            onChange={(e) => setPcMenos(e.target.value)}
            name="pcMenos" 
          />
          <br /><br />

          <label htmlFor="pcErrada">PC Errada:</label>
          <input
            type="number"
            id="pcErrada"
            value={pcErrada}
            onChange={(e) => setPcErrada(e.target.value)}
            name="pcErrada" 
          />
          <br /><br />
        <button type="submit">Salvar</button>
      </form>
    </div>
  );
}

export default SeparationForm;
