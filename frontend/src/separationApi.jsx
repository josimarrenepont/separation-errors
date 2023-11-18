import axios from 'axios';


const BASE_URL = 'http://localhost:8080/separations'; // Substitua pelo URL real do seu backend

const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const saveSeparation = (formData) => {
  return api.put(`http://localhost:8080/separations`, formData);
};

// Outras funções de serviço podem ser adicionadas aqui conforme necessário

export const fetchErrorsByProductCode = async (codProduct) => {
  try {
    const response = await api.get(`http://localhost:8080/separations/separation-error-history/${codProduct}`);
    return response.data;
  } catch (error) {
    console.error('Erro ao buscar erros de separação por código do produto:', error);
    throw error; // você pode escolher tratar o erro de outra maneira ou simplesmente relançá-lo
  }
};
