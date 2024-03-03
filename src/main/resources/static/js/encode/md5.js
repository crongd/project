const submitBtn = document.querySelector('#submit-btn');
const inputText = document.querySelector('#inputText');
const outputText = document.querySelector('#outputText');
console.log(submitBtn);
submitBtn.onclick = () => {
    console.log('클림됨');
    console.log(inputText.value)
    fetch(`/encode/md5`, {
        method: 'POST',
        body: inputText.value
    })
        .then(resp => resp.text())
        .then(result => {
            outputText.innerText = result;
        })
}