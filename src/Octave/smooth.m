% Prevent Octave from thinking that this is a function file:
1;

function smooth_and_save(input_file, output_file, window_value, iterations)
  for i = 1:iterations
    if i == 1
      % Use 'salted-points.csv' as input for first iteration
      current_input_file = input_file;
    else
      % After first use the previously smoothed file as input for next iterations
      prev_output_file = sprintf('%s_iteration%d.csv', output_file, i - 1);
      current_input_file = prev_output_file;
    end

    % Read data from the current input file
    input_data = csvread(current_input_file);

    % Smooth the data using movmean
    smoothed_data = movmean(input_data, window_value);

    % Create a unique output file for each iteration
    unique_output_file = sprintf('%s_iteration%d.csv', output_file, i);

    % Save the smoothed data to the output file
    csvwrite(unique_output_file, smoothed_data);
  end
end

% Call the smooth_and_save with window value and iterations
smooth_and_save('salted-points.csv', 'smoothed-points', 4, 20);
