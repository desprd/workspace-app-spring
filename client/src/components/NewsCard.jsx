import React from "react";

function NewsCard({ news }) {
  return (
    <div className="mb-10 max-w-70">
      <a href={news.url}>
        <div className="flex flex-col">
          <img className="w-70 h-40" src={news.urlToImage} alt="" />
          <p className="font-semibold">{news.title}</p>
        </div>
      </a>
    </div>
  );
}

export default NewsCard;
