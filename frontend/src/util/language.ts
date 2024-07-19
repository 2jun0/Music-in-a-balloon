export function getLanguage() {
  return navigator.language.substring(0, 2);
}

export function getCountry() {
  for (let i = 0; i < navigator.languages.length; i += 1) {
    const language = navigator.languages[i];
    const country = language.substring(language.length - 2, language.length);
    if (country) {
      return country;
    }
  }

  return null;
}
