const submitBtn = document.querySelector('#submit-btn');
const inputText = document.querySelector('#inputText');
const outputText = document.querySelector('#outputText');
submitBtn.onclick = () => {
    console.log(inputText.value)
    fetch(`/encode/sha512`, {
        method: 'POST',
        body: inputText.value
    })
        .then(resp => resp.text())
        .then(result => {
            outputText.innerText = result;
        })
}