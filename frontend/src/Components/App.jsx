import React, { useState } from 'react';
import ReactDOM from 'react-dom';
import MeuBotao from './components/MeuBotao';


// eslint-disable-next-line react-refresh/only-export-components
function App() {
  const [cliques, setCliques] = useState(0);

  const handleClique = () => {
    setCliques(cliques + 1);
  };

  return (
    <div>
      <h1>Erros de Separação</h1>
      <MeuBotao onClick={handleClique} />
      <p>Cliques: {cliques}</p>
    </div>
  );
}

ReactDOM.render(<App />,document.getElementById('root'));
