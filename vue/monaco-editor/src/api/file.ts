import request from '@/utils/request';


//get
export function getFile(name: string) {
    return request({ url: "/file/" + name });
}

//put
export function putFile(file: any) {
    return request({
        url: "/file/" + file.name,
        method: 'put',
        data: file
    });
}

