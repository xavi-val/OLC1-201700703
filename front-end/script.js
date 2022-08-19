const button_upload = document.querySelector(".open_file");
const file_selector = document.querySelector("#select_file");
const text_area1 = document.querySelector("#input1");
let selected_file;

button_upload.onclick = function () {
  file_selector.click();
};

file_selector.onchange = function () {
  selected_file = this.files[0];

  let reader = new FileReader();
  reader.onload = () => {    
    text_area1.value = reader.result;
    console.log(reader.result)
  };
  reader.readAsText(selected_file);

  

};
