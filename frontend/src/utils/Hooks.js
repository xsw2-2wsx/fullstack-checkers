import React, { useState } from "react";

export const useLocalStorage = (keyName, defaultValue) => {
    const [storedValue, setStoredValue] = useState(localStorage.getItem(keyName));

    const setValue = (value) => {
        localStorage.setItem(keyName, value);
        setStoredValue(value);
    }
  
    return [storedValue, setValue];
}