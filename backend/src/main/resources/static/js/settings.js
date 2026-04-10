const credentials = sessionStorage.getItem("credentials");

if (!credentials) {
  window.location.href = "index.html";
}

const headers = {
  Authorization: "Basic " + credentials,
  "Content-Type": "application/json",
};

function logout() {
  sessionStorage.removeItem("credentials");
  window.location.href = "index.html";
}

function loadCurrencies() {
  fetch("/api/rates/currencies", { headers })
    .then((response) => response.json())
    .then((currencies) => {
      const select = document.getElementById("base-currency");
      select.innerHTML = "";
      for (const [code, name] of Object.entries(currencies)) {
        select.innerHTML += `<option value="${code}">${code} - ${name}</option>`;
      }

      const list = document.getElementById("currencies-list");
      list.innerHTML = "";
      for (const [code, name] of Object.entries(currencies)) {
        list.innerHTML += `
                    <label style="display: block; margin: 5px 0;">
                        <input type="checkbox" value="${code}"> ${code} - ${name}
                    </label>`;
      }

      loadCurrentSettings(currencies);
    });
}

function loadCurrentSettings() {
  fetch("/api/settings", { headers })
    .then((response) => response.json())
    .then((settings) => {
      if (!settings) return;

      document.getElementById("base-currency").value =
        settings.preferredCurrency;

      const checkboxes = document.querySelectorAll(
        '#currencies-list input[type="checkbox"]',
      );
      checkboxes.forEach((checkbox) => {
        if (settings.selectedCurrencies.includes(checkbox.value)) {
          checkbox.checked = true;
        }
      });
    });
}

function saveSettings() {
  const baseCurrency = document.getElementById("base-currency").value;
  const checkboxes = document.querySelectorAll(
    '#currencies-list input[type="checkbox"]:checked',
  );
  const selectedCurrencies = Array.from(checkboxes).map((cb) => cb.value);

  if (selectedCurrencies.length === 0) {
    alert("Vyberte alespoň jednu měnu!");
    return;
  }

  fetch("/api/settings", {
    method: "POST",
    headers,
    body: JSON.stringify({
      preferredCurrency: baseCurrency,
      selectedCurrencies: selectedCurrencies,
    }),
  })
    .then((response) => {
      if (response.ok) {
        document.getElementById("success-message").classList.remove("hidden");
        document.getElementById("error-message").classList.add("hidden");
        setTimeout(() => {
          document.getElementById("success-message").classList.add("hidden");
        }, 3000);
      } else {
        document.getElementById("error-message").classList.remove("hidden");
      }
    })
    .catch(() => {
      document.getElementById("error-message").classList.remove("hidden");
    });
}

loadCurrencies();
