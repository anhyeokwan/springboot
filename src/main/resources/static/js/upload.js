async function uploadToServer(formObj) {
    console.log("upload to server.....");
    console.log(formObj);

    const respone = await axios({
        method: 'post',
        url: '/upload',
        data: formObj,
        headers: {
            'Content-Type': 'multipart-form-data'
        },
    });
    return respone.data;
}

async function removeFileToServer(uuid, fileName) {
    const response = await axios.delete(`/remove/${uuid}_${fileNmae}`);

    return response.data;
}