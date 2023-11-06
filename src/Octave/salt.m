% Prevent Octave from thinking that this is a function file:
1;

function salt(input_file, output_file)
  % Read points csv
  input_data = csvread(input_file);

  % Range for salting
  min_range = 0;
  max_range = 100;

  % Array to for salted y-values
  salted_y = input_data(:, 2); % y-values in second column

  % Determine whether to add or subtract randomly
    for i = 1:size(salted_y, 1)
    % Generate random salt value
    num = (max_range - min_range) * rand() + min_range;

    % Determine whether to add or subtract randomly
    if rand() < 0.5
      salted_y(i) = salted_y(i) + num;
    else
      salted_y(i) = salted_y(i) - num;
    end
  end

   % Copy input_data and replacing y-values with salted_y
  salted_data = input_data;
  salted_data(:, 2) = salted_y;

  % Save the salted data to a new CSV file
  csvwrite(output_file, salted_data);
end

salt('octave-points.csv', 'salted-points.csv');
