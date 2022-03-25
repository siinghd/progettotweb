const genericPost = async (url, data) => {
  const headersList = {
    Accept: "application/json",
    "Content-Type": "application/x-www-form-urlencoded",
  };

  return fetch(url, {
    method: "POST",
    body: data,
    headers: headersList,
    credentials: "include",
  })
    .then((response) => {
      return response.json();
    })
    .catch((err) => {
      return { status: "fail", message: "Server error", err };
    });
};
const genericGet = async (url) => {
  const headersList = {
    Accept: "application/json",
  };
  return fetch(url, {
    method: "GET",
    headers: headersList,
    credentials: "include",
  })
    .then((response) => {
      return response.json();
    })
    .catch((err) => {
      return { status: "fail", message: "Server error", err };
    });
};

const genericUpdate = async (url, data) => {
  const headersList = {
    Accept: "application/json",
    "Content-Type": "application/x-www-form-urlencoded",
  };

  return fetch(url, {
    method: "PUT",
    body: data,
    headers: headersList,
    credentials: "include",
  })
    .then((response) => {
      return response.json();
    })
    .catch((err) => {
      return { status: "fail", message: "Server error", err };
    });
};
const genericDelete = async (url) => {
  const headersList = {
    Accept: "application/json",
  };

  return fetch(url, {
    method: "DELETE",
    headers: headersList,
    credentials: "include",
  })
    .then((response) => {
      return response.json();
    })
    .catch((err) => {
      return { status: "fail", message: "Server error", err };
    });
};
module.exports = { genericPost, genericGet, genericUpdate, genericDelete };
