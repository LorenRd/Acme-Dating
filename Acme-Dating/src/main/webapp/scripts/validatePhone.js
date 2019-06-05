const PHONE_PATTERN = /^(\+[0-9]{1,3}[ ]{0,1}(\([0-9]{1,3}\)[ ]{0,1}){0,1}){0,1}[0-9]{1}[0-9 ]{3,}$/;
function validatePhone(message, countryCode) {
  let phone = document.getElementById("phone").value;
  if (phone.length && phone.match(PHONE_PATTERN) && phone.match(countryCode))
	  return true;
  else if (confirm(message)) {
	  return true;
  } else return false;
};