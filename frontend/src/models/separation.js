import { query } from '../db'; // Importe a configuração do banco de dados

// Modelo de dados Separation

function createSeparation(separation) {

return query(

'INSERT INTO tb_separation (id, date, name, pallet, cod_product, error_pc_errada, error_pc_mais, error_pc_menos, pc_errada, pc_mais, pc_menos) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11) RETURNING *',

[

separation.employeeId,

separation.date,

separation.pallet,

separation.codProduct,

separation.errorPcMais,

separation.errorPcMenos,

separation.errorPcErrada,

separation.pcMais,

separation.pcMenos,

separation.pcErrada,


]

);

}

// eslint-disable-next-line no-undef
export default { createSeparation };