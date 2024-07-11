/* eslint-disable @typescript-eslint/no-var-requires */
const HtmlWebpackPlugin = require('html-webpack-plugin');
const Dotenv = require('dotenv-webpack');
const webpack = require('webpack');
const { convertToAbsolutePath } = require('./webpackUtil');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');
const CopyPlugin = require('copy-webpack-plugin');

module.exports = {
  entry: convertToAbsolutePath('src/index.tsx'),
  module: {
    rules: [
      {
        test: /\.(js|jsx|ts|tsx)$/,
        exclude: /node_modules/,
        loader: 'esbuild-loader',
        options: {
          target: 'es2021',
        },
      },
      {
        test: /\.svg$/i,
        issuer: /\.(style.js|style.ts)$/,
        use: ['url-loader'],
      },
      {
        test: /\.svg$/i,
        type: 'asset',
        resourceQuery: /url/, // *.svg?url
      },
      {
        test: /\.svg$/i,
        type: 'asset/source',
        resourceQuery: /raw/, // *.svg?raw
      },
      {
        test: /\.svg$/i,
        issuer: /\.[jt]sx?$/,
        resourceQuery: { not: [/url/, /raw/] },
        use: ['@svgr/webpack'],
      },
      {
        test: /\.(png|jpg)$/i,
        issuer: /\.[jt]sx?$/,
        use: ['url-loader'],
      },
      {
        test: /\.(css|scss)$/i,
        use: ['style-loader', 'css-loader'],
      },
    ],
  },

  output: {
    path: convertToAbsolutePath('dist'),
    filename: '[name].[chunkhash].bundle.js',
    publicPath: '/',
    clean: true,
  },

  resolve: {
    extensions: ['.js', '.ts', '.jsx', '.tsx', '.json'],
    alias: {
      '@': convertToAbsolutePath('src'),
      '@component': convertToAbsolutePath('src/component'),
      '@type': convertToAbsolutePath('src/type'),
      '@hook': convertToAbsolutePath('src/hook'),
      '@page': convertToAbsolutePath('src/page'),
      '@style': convertToAbsolutePath('src/style'),
      '@constant': convertToAbsolutePath('src/constant'),
      '@asset': convertToAbsolutePath('src/asset'),
      '@api': convertToAbsolutePath('src/api'),
      '@mock': convertToAbsolutePath('src/mock'),
      '@storie': convertToAbsolutePath('src/storie'),
      '@router': convertToAbsolutePath('src/router'),
      '@store': convertToAbsolutePath('src/store'),
      '@util': convertToAbsolutePath('src/util'),
    },
  },
  plugins: [
    new webpack.ProvidePlugin({
      React: 'react',
    }),
    new HtmlWebpackPlugin({
      template: convertToAbsolutePath('public/index.html'),
      favicon: convertToAbsolutePath('public/favicon.ico'),
    }),
    new CopyPlugin({
      patterns: [{ from: 'public/_redirects', to: '' }],
    }),
    new Dotenv(),
    new ForkTsCheckerWebpackPlugin(),
  ],
};
