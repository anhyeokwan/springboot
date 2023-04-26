async function get1(bno){
    const result = await axios.get(`/replies/list/${bno}`);

    //console.log(result);

    return result;
}

async function getList({bno, page, size, goLast}) {
    const result = await axios.get(`/replies/list/${bno}`, {params: {page, size}})

    if (goLast) {
        const total = result.data.total;
        const lastPage = parseInt(Math.ceil(total / size));
        return getList({bno:bno, page:lastPage, size:size});
    }

    return result.data;
}

async function addReply(replyObj) {
    const response = await axios.post(`/replies/`, replyObj);

    return response.data;
}

async function getReply(rno) {
    const respone = await axios.get(`/replies/${rno}`);
    return respone.data;
}

async function modifyReply(replyObj) {
    const respone = await axios.put(`/replies/${replyObj.rno}`);
    return respone.data;
}

async function removeReply(rno) {
    const respone = await axios.delete(`/replies/${rno}`);
    return respone.data;
}