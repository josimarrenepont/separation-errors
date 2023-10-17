import { query } from '../db'; // Importe a configuração do banco de dados

// Modelo de dados Employee

function createEmployee(employee) {

return query(

'INSERT INTO employees (name, total_errors) VALUES ($1, $2) RETURNING *',

[employee.name, employee.totalErrors]

);

}

// Função para atualizar o total de erros de um funcionário

function updateTotalErrors(employeeId, totalErrors) {

return query('UPDATE employees SET total_errors = $1 WHERE id = $2', [

totalErrors,

employeeId,

]);

}

// eslint-disable-next-line no-undef
export default { createEmployee, updateTotalErrors };