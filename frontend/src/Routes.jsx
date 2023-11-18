// routes.js
import { Route, BrowserRouter as Router, Switch } from 'react-router-dom';
import ErrosDeSeparacao from './ErrosDeSeparacao';
import SeparationForm from './SeparationForm';

function Routes() {
  return (
    <Router>
      <Switch>
        <Route path="/" exact component={ErrosDeSeparacao} />
        <Route path="/separation-form" component={SeparationForm} />
      </Switch>
    </Router>
  );
}

export default Routes;
