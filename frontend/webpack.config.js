const path = require("path")
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack')

const envName = process.env.NODE_ENV;
const env = require(`./env.${envName}`);
const definePlugin = new webpack.DefinePlugin(env);

module.exports = {
    entry: "./src/index.js",
    output: {
        filename: "bundle.js",
        path: path.resolve(__dirname, "dist"),
        publicPath: '/'
    },
    module: {
        rules: [
            {
                test: /\.m?js$/,
                exclude: /node_modules/,
                use: {
                  loader: "babel-loader",
                  options: {
                    presets: ['@babel/preset-react']
                  }
                }
              },
              {
                test: /\.s[ac]ss$/i,
                use: [
                  "style-loader",
                  "css-loader",
                  "sass-loader",
                ],
              },
              {
                test: /\.css$/i,
                use: [
                  "style-loader",
                  "css-loader",
                ],
              },
              {
                test: new RegExp(`^${path.resolve(__dirname, 'src', 'assets')}/.*`),
                type: 'asset/resource'
              }
        ]
    },
    mode: "development",
    devServer: {
      static: {
        directory: path.join(__dirname, 'dist'),
      },
      compress: true,
      port: 3000,
      historyApiFallback: {
        index: '/'
      }
    },
    resolve: {
      modules: ['node_modules', 'src', 'src/assets']
    },
    plugins: [
      new HtmlWebpackPlugin({ inject: 'head' }),
      definePlugin
    ],
    performance: {
      maxEntrypointSize: 512000,
      maxAssetSize: 512000
  }
}