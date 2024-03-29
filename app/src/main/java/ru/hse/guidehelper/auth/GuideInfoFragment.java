package ru.hse.guidehelper.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.Objects;
import java.util.regex.Pattern;

import ru.hse.guidehelper.MainActivity;
import ru.hse.guidehelper.R;
import ru.hse.guidehelper.api.RequestHelper;

public class GuideInfoFragment extends Fragment {
    private EditText editLocation;
    private EditText editMobilePhone;
    private EditText editDescription;
    private AwesomeValidation awesomeValidation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_guide_info, container, false);

        awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        awesomeValidation.setContext(root.getContext());

        editLocation = root.findViewById(R.id.editLocation);
        if (!Objects.equals(MainActivity.currentUser.getCity(), null)) {
            editLocation.setText(MainActivity.currentUser.getCity());
        }

        editMobilePhone = root.findViewById(R.id.editMobilePhone);
        if (!Objects.equals(MainActivity.currentUser.getPhoneNumber(), null)) {
            editMobilePhone.setText(MainActivity.currentUser.getPhoneNumber());
        }

        editDescription = root.findViewById(R.id.editDescription);
        if (!Objects.equals(MainActivity.currentUser.getDescription(), null)) {
            editDescription.setText(MainActivity.currentUser.getDescription());
        }
        Button saveChangesButton = root.findViewById(R.id.saveChangesButton);
        saveChangesButton.setOnClickListener(view -> {
            if (awesomeValidation.validate()) {
                Toast.makeText(GuideInfoFragment.this.getActivity(), "Сохранено", Toast.LENGTH_LONG).show();
                // обновить пользователя в БД
                String token = RequestHelper.getToken(MainActivity.currentUser.getId());
                if (token == null && MainActivity.getToken() != null) {
                    token = MainActivity.getToken();
                }
                MainActivity.currentUser
                        .setCity(editLocation.getText().toString())
                        .setPhoneNumber(editMobilePhone.getText().toString())
                        .setDescription(editDescription.getText().toString())
                        .setIsGuide(true)
                        .setToken(token);
                RequestHelper.updateUser(MainActivity.currentUser, MainActivity.currentUser.getId());
                RequestHelper.updateToken(MainActivity.currentUser.getId(), token);
                GuideInfoFragment.this.requireActivity().onBackPressed();
                GuideInfoFragment.this.requireActivity().onBackPressed();

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_profile);
            }
        });

        awesomeValidation.addValidation(editLocation, Pattern.compile(getString(R.string.NonEmptyStringRegexp)), "Локация не должна быть пустой!");
        awesomeValidation.addValidation(editLocation, s -> s.length() < 255, "Название локации слишком большое!");
        awesomeValidation.addValidation(editMobilePhone, Patterns.PHONE, "Введите корректный номер!");
        awesomeValidation.addValidation(editDescription, Pattern.compile(getString(R.string.NonEmptyStringRegexp)), "Описание не должно быть пустым!");

        return root;
    }
}
