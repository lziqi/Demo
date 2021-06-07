import axios from "axios";
import {
  AxiosPromise,
  AxiosInstance,
  AxiosRequestConfig,
  AxiosResponse,
  AxiosError,
} from "axios";

import { ElMessage } from "element-plus";

const request: AxiosInstance = axios.create({
  baseURL: "https://jsonplaceholder.typicode.com",
});

/**设置请求 */
request.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    if (sessionStorage.getItem("token")) {
      config.headers.authorization = sessionStorage.getItem("token");
    }
    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  }
);

/**设置响应 */
request.interceptors.response.use(
  (response: AxiosResponse) => {
    const { data } = response;
    // if (data.code !== 200 && data.code !== 201) {
    //   ElMessage.error(data.msg);
    // }
    return response;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  }
);

export const get = (url: string, params: any): AxiosPromise => {
  return request.get(url, { params });
};

export const post = (url: string, data: any): AxiosPromise => {
  return request.post(url, data);
};

export const put = (url: string, data: any): AxiosPromise => {
  return request.put(url, data);
};

export const del = (url: string): AxiosPromise => {
  return request.delete(url);
};
