function login() {
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  if (!username || !password) {
    document.getElementById("error-message").classList.remove("hidden");
    return;
  }

  const credentials = btoa(username + ":" + password);

  fetch("/api/settings", {
    headers: {
      Authorization: "Basic " + credentials,
    },
  })
    .then((response) => {
      if (response.ok) {
        sessionStorage.setItem("credentials", credentials);
        window.location.href = "dashboard.html";
      } else {
        document.getElementById("error-message").classList.remove("hidden");
      }
    })
    .catch(() => {
      document.getElementById("error-message").classList.remove("hidden");
    });
}
