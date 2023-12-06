/* eslint-disable @typescript-eslint/no-unused-vars */
import axios from 'axios';
import React, { useEffect, useState } from 'react';

function SeparationForm() {
  const [date, setDate] = useState('date.Date');
  const [employee, setEmployee] = useState('employee.name');
  const [pallet, setPallet] = useState('pallet.parseInt');
  const [codProduct, setCodProduct] = useState('codProduct.parseInt');
  const [errorPcMais, setErrorPcMais] = useState('errorPcMais.parseInt');
  const [errorPcMenos, setErrorPcMenos] = useState('errorPcMenos.parseInt');
  const [errorPcErrada, setErrorPcErrada] = useState('errorPcErrada.parseInt');
  const [successMessage, setSuccessMessage] = useState('');
  const [errorHistory, setErrorHistory] = useState([]);
  const [formSubmitted, setFormSubmitted] = useState(false);
  
  useEffect(() => {
    if (formSubmitted) {
      // Recarrega a página após 1 segundo (1000 milissegundos)
      const timeout = setTimeout(() => {
        window.location.reload();
      }, 1000);

      // Limpa o timeout se o componente for desmontado antes do tempo
      return () => clearTimeout(timeout);
    }
  }, [formSubmitted]);

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
                errorPcMais: (employeeData.errorPcMais || 0) + errorData.errorPcMais,
                errorPcMenos: (employeeData.errorPcMenos || 0) + errorData.errorPcMenos,
                errorPcErrada: (employeeData.errorPcErrada || 0) + errorData.errorPcErrada,
                subTotPcMais: (employeeData.subTotPcMais || 0) + errorData.errorPcMais,
                subTotPcMenos: (employeeData.subTotPcMenos || 0) + errorData.errorPcMenos,
                subTotPcErrada: (employeeData.subTotPcErrada || 0) + errorData.errorPcErrada,
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
      setErrorPcMais('');
      setErrorPcMenos('');
      setErrorPcErrada('');
      setErrorHistory([]);

      setFormSubmitted(true);

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

          <label htmlFor="codProduct">Código do Produto:</label>
          <input
            type="number"
            id="codProduct"
            value={codProduct}
            onChange={(e) => setCodProduct(e.target.value)}
            name="codProduct"
            required
          />
          <br /><br />

          <label htmlFor="errorPcMais">Peça Mais:</label>
          <input
            type="number"
            id="errorPcMais"
            value={errorPcMais}
            onChange={(e) => setErrorPcMais(e.target.value)}
            name="errorPcMais"
            
          />
          <br /><br />

          <label htmlFor="errorPcMenos">Peça Menos:</label>
          <input
            type="number"
            id="errorPcMenos"
            value={errorPcMenos}
            onChange={(e) => setErrorPcMenos(e.target.value)}
            name="errorPcMenos"
            
          />
          <br /><br />

          <label htmlFor="errorPcErrada">Peça Errada:</label>
          <input
            type="number"
            id="errorPcErrada"
            value={errorPcErrada}
            onChange={(e) => setErrorPcErrada(e.target.value)}
            name="errorPcErrada"   
          />
          <br /><br />
          <button className="custom-button" type="submit">Enviar</button>
      </form>
    </div>
  );
}

export default SeparationForm;
