http=require('http');

url=require('url');

querystring = require('querystring');

function  onRequest(req,res){

var path = url.parse(req.url).pathname;

var query =url.parse(req.url).query;

var no1 =querystring.parse(query)["n1"];

var no2=querystring.parse(query)["n2"];

var sum=parseInt(no1)+parseInt(no2);

console.log(sum);

res.write("The result is "+" " + sum);

res.end();

}

http.createServer(onRequest).listen(4001);

console.log('Server has Started.......');