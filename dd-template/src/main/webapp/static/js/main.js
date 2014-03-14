function go(url)
{
 window.location = url;
}
 
function confirmAndGo(message, url)
{
 var isOK = confirm(message);
 if(isOK)
 {
  go(url);
 }
}