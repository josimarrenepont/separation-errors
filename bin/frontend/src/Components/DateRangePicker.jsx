import React, { useState } from 'react';

function DateRangePicker({ onDateRangeChange }) {

const [startDate, setStartDate] = useState('');

const [endDate, setEndDate] = useState('');

const handleDateRangeChange = () => {

if (startDate && endDate) {

onDateRangeChange(startDate, endDate);

}

};

return (

<div>

<input

type="date"

value={startDate}

onChange={(e) => setStartDate(e.target.value)}

placeholder="Data de início"

/>

<input

type="date"

value={endDate}

onChange={(e) => setEndDate(e.target.value)}

placeholder="Data de término"

/>

<button onClick={handleDateRangeChange}>Selecionar Período</button>

</div>

);

}

export default DateRangePicker;