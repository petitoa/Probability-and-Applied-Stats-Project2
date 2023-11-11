# Not function file:

1;

% Define range for x values 0 - 385 with .2 increment
x = 0:0.2:40;

% Function to calculate the data
function y = createData(x_input)
    y = (x_input - 20).^2;
end

% Call the function to get y values
y = createData(x);

% Combine x and y into a matrix
data = [x', y'];

filename = 'octave-points.csv';

% Write the data to the CSV file
csvwrite(filename, data);

% Plot the results
plot(x, y);

