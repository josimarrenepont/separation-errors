
import './ErrosDeSeparacao';
import './components/App';
import './components/SeparationForm';
import './styles/separationForm.css';

function SeparationForm() {

return (

<div className="container">
    <h1>Formulário de Separação</h1>
    <div className='SeparationForm'>
    

    <form method="post" action="/salvar-separacao">

    <label htmlFor="data">Data:</label>

    <input type="date" id="data" name="data" required /><br /><br />

    <label htmlFor="employee">Funcionário:</label>

    <select id="employee" name="employee" required>

    <option value="Alexandre">Alexandre</option>

    <option value="Denison">Denison</option>

    <option value="Gilvan">Gilvan</option>

    <option value="Gildoberto">Gildoberto</option>

    </select><br /><br />

    <label htmlFor="separation">Separação:</label>

    <select id="separation" name="separation" required>

    <option value="pcMais">pcMais</option>

    <option value="pcMenos">pcMenos</option>

    <option value="pcErrada">pcErrada</option>

    </select><br /><br />

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