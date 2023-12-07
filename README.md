# Probability and Applied Statistics Project 2

## Overview

This repository features a collection of work related to the Probability and Applied Statistics course. It includes, a formula sheet, stock-bot simulator, a stats library, and written work.

## Table of Contents
- [Built With](#built-with)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Stats Library](#stats-library)
- [Plotting, Salting, and Smoothing](#plotting-salting-and-smoothing)
    - [PSS 1 - Java Function Graphing](#pss-1-java-function-graphing)
    - [PSS 2 - Octave](#pss-2-octave)
    - [PSS 3 - JFreeChart and Apache Stats Library](#pss-3-jfreechart-and-apache-stats-library)
- [Stock Simulator](#stock-simulator)
- [Miscellaneous](#miscellaneous)
- [Final Report](#final-report)

- [Extra Credit Considerations](#extra-credit-considerations)

## Built With
- Java
- JFreeChart
- Apache Commons Math Library (Version 3)

## Prerequisites
- Java
- JFreeChart
- Apache Commons Math Library (Version 3)

## Installation

To clone the repository to your local machine, use the following command:
```bash
git clone https://github.com/petitoa/Probability-and-Applied-Stats.git
```

## Stats Library

### Chapter 4

- Uniform Distribution Probability 
  - Expected Value
  - Variance
- Determining Independence or Dependence (Marginal Density)

## Stock Simulator

### Overview

The Stock Simulator tests 3 algorithms. It contains a network and stock object which can be occupied with CSV files. After simulated trading can be performed.

### Key Features

####  **Stock Trading Simulation**
This project combines skills learned in class to load stock data, model stock data, and back-test 3 separate algorithms using simulations on the loaded stock data.

### Collecting Stock Data

1. Stock Data collected from [Yahoo Finance](https://finance.yahoo.com/).
2. Monitor the AMZN stock.
3. Used "Download Historical Data" to get the weekly CSV.

### Three Algorithms

1. **Buy and Hold:**
  - Buy stocks with complete net worth. Sell all stocks on the last day

2. **RSI and Moving Average:**
  - Combine RSI and Moving Average to guide buy and sell decisions.

3. **RSI and Heuristic Trade Evaluator:**
  - A trading algorithm that establishes a portfolio. 

## Testing

Evaluates each algorithm's performance. Displays RSI for all days and the smoothed open values of the stocks with JFreeChart.

## Plotting Salting and Smoothing

- ### PSS 1 Java Function Graphing
In this section, a Java program is developed to graph a chosen function. The code, screenshots, and experimentation results with parameter variations are included.

- ### PSS 2 Octave
A tutorial and documentation of the code and process for smoothing, salting, and plotting functions using Octave. Code, screenshots, and detailed explanations are provided.

- ### PSS 3 JFreeChart and Apache Stats Library
Utilization of JFreeChart, and Apache Stats Library for graphing. The results are documented with write-ups, screenshots, and code snippets.

## Miscellaneous

### Formula Sheet
- A comprehensive list of all the formulas covered in lectures and the textbook.

### Beta, Gamma, and Normal Distribution paper
- Paper explaining use cases, when, and how to use beta and gamma distribution.

## Final Report
- Uses a K-pop Database sourced from [Kaggle](https://www.kaggle.com/datasets/nicolsalayoarias/all-kpop-idols/data)
- Perform statistical and probabilistic analysis to reveal insights into the industry.

## Extra Credit Considerations
Extra credit considerations can be found in [ExtraCredit.txt](src/ExtraCredit.txt)
