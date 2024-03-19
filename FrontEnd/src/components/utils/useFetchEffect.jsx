import { useState, useEffect } from "react";

export function useFetchEffect(url) {
    const [data, setData] = useState(null);
    //const [error, setError] = useState(null);

    useEffect(() => {
        const traeData = async () => {
            const response =  await fetch(url);
            setData(await response.json());
        }

        traeData(); 

    }, []);

    /*
    useEffect(() => {
    fetch(url)
        .then((response) => response.json())
        .then((data) => setData(data))
        .catch((error) => setError(error));
    }, []);
    */

    return { data };
}