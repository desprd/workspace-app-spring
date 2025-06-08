import React from "react";

function ForecastPanel({ weather }) {
  function getWeatherDescription(code) {
    const weatherCodes = {
      0: ["Clear sky", "/sunny(1).png"],
      1: ["Mainly clear", "/sunny_s_cloudy.png"],
      2: ["Partly cloudy", "/cloudy_s_sunny.png"],
      3: ["Overcast", "/cloudy.png"],
      45: ["Fog", "/fog.png"],
      48: ["Depositing rime fog", "/fog.png"],
      51: ["Light drizzle", "/rain_light.png"],
      53: ["Moderate drizzle", "/rain_light.png"],
      55: ["Dense drizzle", "/rain_light.png"],
      56: ["Light freezing drizzle", "/rain_light.png"],
      57: ["Dense freezing drizzle", "/rain_light.png"],
      61: ["Slight rain", "/rain.png"],
      63: ["Moderate rain", "/rain.png"],
      65: ["Heavy rain", "/rain_heavy.png"],
      66: ["Light freezing rain", "/rain_light.png"],
      67: ["Heavy freezing rain", "/rain_heavy.png"],
      71: ["Slight snow fall", "/snow_light.png"],
      73: ["Moderate snow fall", "/snow.png"],
      75: ["Heavy snow fall", "/snow_heavy.png"],
      77: ["Snow grains", "/snow_heavy.png"],
      80: ["Slight rain showers", "/rain.png"],
      81: ["Moderate rain showers", "/rain.png"],
      82: ["Violent rain showers", "/rain_heavy.png"],
      85: ["Slight snow showers", "/snow.png"],
      86: ["Heavy snow showers", "/snow_heavy.png"],
      95: ["Thunderstorm", "/thunderstorms.png"],
      96: ["Thunderstorm with slight hail", "/thunderstorms.png"],
      99: ["Thunderstorm with heavy hail", "/thunderstorms.png"],
    };

    return weatherCodes[code] || "Unknown";
  }
  return (
    <div>
      <div className="flex items-center gap-3">
        <img
          className="w-10 h-10"
          src={getWeatherDescription(weather.weather_code)[1]}
          alt=""
        />
        <div className="flex flex-col text-xl">
          <div>{weather.time.slice(0, 10)}</div>
          <div className="flex gap-2">
            <div>
              {`${weather.temperature_2m > 0 ? "+" : ""}`}
              {Math.round(weather.temperature_2m)}
            </div>
            {getWeatherDescription(weather.weather_code)[0]}
          </div>
        </div>
      </div>
    </div>
  );
}

export default ForecastPanel;
