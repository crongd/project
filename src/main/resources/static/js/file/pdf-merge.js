const submitBtn = document.querySelector('#submit-btn');


submitBtn.onclick = () => {

    let data = new FormData();
    for (let file of fileData) {
        data.append("pdfs", file);
    }

    fetch('/file/pdf-merge', {
        method: 'POST',
        body: data
    }).then(resp => console.log(resp.ok))
}