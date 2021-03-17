import axios from "axios";

export default function customAxios(url, callback){
    axios(
        {
            url: url,
            method :'get',
            baseURL: 'http://localhost',
            withCredentials: true,
        }
    ).then(function (response){
       callback(response.data);
    });
}