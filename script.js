const button_upload = document.querySelector(".open_file");
const file_selector = document.querySelector("#select_file");
const save_file = document.querySelector(".save_file");
const text_area1 = document.querySelector("#input1");
const text_area2 = document.querySelector("#input2");
const error_text_area = document.querySelector("#input3");
const button_go = document.querySelector("#button_go");
const button_python = document.querySelector("#button_python");
const flowChartButton = document.querySelector("#flowChartID");
const filaColumnaLabel = document.querySelector("#filaColumna");

/*MENU METHODS*/
let selected_file;
let ip = "192.168.1.11";
text_area1.value = "inicio \n\nfin";
text_area2.value = "";
error_text_area.value="";
let imageUrl;

/* Subir un documento y cargarlo al text area */
button_upload.onclick = function () {
  file_selector.click();
};

/* Subir un documento y cargarlo al text area */
file_selector.onchange = function () {
  selected_file = this.files[0];

  let reader = new FileReader();
  reader.onload = () => {
    text_area1.value = reader.result;
  };
  reader.readAsText(selected_file);
};

/*  */
save_file.onclick = () => {
  const date = new Date();
  let time = `${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;
  download(`Code_${time}.olc`);
};

function download(name) {
  let file = new Blob([text_area1.value], { type: "text/plain" });
  save_file.href = URL.createObjectURL(file);
  save_file.download = name;
}

flowChartButton.onclick = function () {
  window.open(imageUrl, "Flow Chart");
};


/* OBTENER FILA Y COLUMNA EN DONDE ESTA EL CURSOR */

function filaColumna(){
  let textLines = text_area1.value.substr(0, text_area1.selectionStart).split("\n");
  let currentLineNumber = textLines.length;
  let currentColumnIndex = textLines[textLines.length-1].length+1;
  filaColumnaLabel.innerHTML = "Fila: "+ currentLineNumber+" Columna: " + currentColumnIndex ;  
}

text_area1.addEventListener("keyup",filaColumna);
text_area1.addEventListener("keydown",filaColumna);
text_area1.addEventListener("mouseup",filaColumna);


/*AJAX METHODS*/

async function go() {
  let res = await fetch(`http://${ip}:8080/go`, {
    method: "POST",
    body: text_area1.value,
  });
  let data = await res.json();

  console.log(data);

  text_area2.value = data.traduccion;

  //IMAGEN DE RESPUESTA
  let blob = new Blob([data.image], { type: "image/svg+xml" });
  imageUrl = URL.createObjectURL(blob);

  //Errores
  error_text_area.value = data.error;
}

async function Python() {
  let res = await fetch(`http://${ip}:8080/python`, {
    method: "POST",
    body: text_area1.value,
  });
  let data = await res.json();

  console.log(data);

  text_area2.value = data.traduccion;

  //IMAGEN DE RESPUESTA
  let blob = new Blob([data.image], { type: "image/svg+xml" });
  imageUrl = URL.createObjectURL(blob);

  //Errores
  error_text_area.value = data.error;
}

button_python.onclick = () => {
  Python();
};

button_go.onclick = () => {
  go();
};
