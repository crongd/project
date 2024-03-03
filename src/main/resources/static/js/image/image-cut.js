const submitBtn = document.querySelector('#submit-btn');
const width = document.querySelector('#width');
const height = document.querySelector('#height');
const check = document.querySelector('#check');
const selection = document.querySelector('#image-section');

submitBtn.onclick = () => {
    const files = fileInput.files;
    console.log(files)
    console.log(width.value)
    console.log(height.value)
    console.log(check.checked)
    console.log(selection.value);

    let data = new FormData();
    for (const file of files) {
        data.append('images', file);
    }
    data.append('width', width.value);
    data.append('height', height.value);
    data.append('check', check.checked);
    data.append('position', selection.value);

    fetch('/image/cut', {
        method: 'POST',
        body: data
    }).then(resp => resp.json())
        .then(data => {
            const imgContainer = document.querySelector('#complete-img-container');
            data.forEach(item => {
                console.log("name: " + item.name);
                console.log("image: " + item.image);
                imgContainer.insertAdjacentHTML('beforeend',
                    `<div>
                            <img class="complete-img" src="${item.image}" alt="처리된 이미지"><br>
                            <a href="${item.image}" download="${item.name}">${item.name}</a>
                        </div>`)
            })

        })
        .catch(e => console.log('패치 에러남'))
}