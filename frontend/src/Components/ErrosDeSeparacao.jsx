import React from 'react';
import './components/App';
import './components/MeuBotao';

function ErrosDeSeparacao() {
  return (
    <div>
      <h1>Erros de Separação</h1>
      <form method="post" action="/salvar-separacao">
        <label htmlFor="data">Data:</label>
        <input type="date" id="data" name="data" required /><br /><br />
      
        <label htmlFor="employee">Funcionário:</label>
        <select id="employee" name="employee" required>
          <option value=""></option>
          <option value="Alexandre">Alexandre</option>
          <option value="Denison">Denison</option>
          <option value="Gilvan">Gilvan</option>
          <option value="Gildoberto">Gildoberto</option>
        </select><br/><br/>

        <label htmlFor="separation">Tipo de Erro:</label>
        <select id="separation" name="separation" required>
            <option value=""></option>
            <option value="pcMais">pcMais</option>
            <option value="pcMenos">pcMenos</option>
            <option value="pcErrada">pcErrada</option>
        </select><br /><br />

        <label htmlFor="error">Erro:</label>
        <input type="number" id="error" name="error" required /><br /><br />

        <label htmlFor="palette">Palete:</label>
        <input type="text" id="palette" name="palette" required /><br /><br />

        <label htmlFor="produto">Produto:</label>
        <input type="text" id="produto" name="produto" required /><br /><br />
        
        <input type="submit" value="Salvar" />
      </form>
    </div>
  );
}

export default ErrosDeSeparacao;
