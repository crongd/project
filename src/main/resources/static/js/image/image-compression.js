const submitBtn = document.querySelector('#submit-btn');

submitBtn.onclick = () => {
    const files = fileInput.files;
    console.log(files);

    let data = new FormData();
    for (const file of files) {
        data.append('images', file);
    }

    fetch('/image/compression', {
        method: 'POST',
        body: data
    }).then(resp => resp.json())
        .then(data => {
            const imgContainer = document.querySelector('#complete-img-container');
            data.forEach((item, index) => {
                if (item.size > files[index].size) {
                    alert(files[index].name + ' \n이 이미지는 더이상 압축할 수 없음');
                } else {
                    console.log("name: " + item.name);
                    console.log("image: " + item.image);
                    console.log("size: " + item.size);
                    console.log()
                    imgContainer.insertAdjacentHTML('beforeend',
                        `<div>
                                <img class="complete-img" src="${item.image}" alt="처리된 이미지"><br>
                                <a href="${item.image}" download="${item.name}">${item.name}</a>
                            </div>`)
                }
            })
        })
}