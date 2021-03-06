// Generated by view binder compiler. Do not edit!
package com.iqbalriiaz.seuresearchers.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.iqbalriiaz.seuresearchers.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignupBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView alreadyUserId;

  @NonNull
  public final TextInputEditText emailEt;

  @NonNull
  public final TextInputLayout emailTil;

  @NonNull
  public final RadioButton isStudent;

  @NonNull
  public final RadioButton isTeacher;

  @NonNull
  public final TextInputEditText passwordEt;

  @NonNull
  public final TextInputLayout passwordTil;

  @NonNull
  public final MaterialButton signupBtnId;

  @NonNull
  public final TextView textViewID;

  private ActivitySignupBinding(@NonNull LinearLayout rootView, @NonNull TextView alreadyUserId,
      @NonNull TextInputEditText emailEt, @NonNull TextInputLayout emailTil,
      @NonNull RadioButton isStudent, @NonNull RadioButton isTeacher,
      @NonNull TextInputEditText passwordEt, @NonNull TextInputLayout passwordTil,
      @NonNull MaterialButton signupBtnId, @NonNull TextView textViewID) {
    this.rootView = rootView;
    this.alreadyUserId = alreadyUserId;
    this.emailEt = emailEt;
    this.emailTil = emailTil;
    this.isStudent = isStudent;
    this.isTeacher = isTeacher;
    this.passwordEt = passwordEt;
    this.passwordTil = passwordTil;
    this.signupBtnId = signupBtnId;
    this.textViewID = textViewID;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_signup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.alreadyUserId;
      TextView alreadyUserId = ViewBindings.findChildViewById(rootView, id);
      if (alreadyUserId == null) {
        break missingId;
      }

      id = R.id.emailEt;
      TextInputEditText emailEt = ViewBindings.findChildViewById(rootView, id);
      if (emailEt == null) {
        break missingId;
      }

      id = R.id.emailTil;
      TextInputLayout emailTil = ViewBindings.findChildViewById(rootView, id);
      if (emailTil == null) {
        break missingId;
      }

      id = R.id.isStudent;
      RadioButton isStudent = ViewBindings.findChildViewById(rootView, id);
      if (isStudent == null) {
        break missingId;
      }

      id = R.id.isTeacher;
      RadioButton isTeacher = ViewBindings.findChildViewById(rootView, id);
      if (isTeacher == null) {
        break missingId;
      }

      id = R.id.passwordEt;
      TextInputEditText passwordEt = ViewBindings.findChildViewById(rootView, id);
      if (passwordEt == null) {
        break missingId;
      }

      id = R.id.passwordTil;
      TextInputLayout passwordTil = ViewBindings.findChildViewById(rootView, id);
      if (passwordTil == null) {
        break missingId;
      }

      id = R.id.signupBtnId;
      MaterialButton signupBtnId = ViewBindings.findChildViewById(rootView, id);
      if (signupBtnId == null) {
        break missingId;
      }

      id = R.id.textViewID;
      TextView textViewID = ViewBindings.findChildViewById(rootView, id);
      if (textViewID == null) {
        break missingId;
      }

      return new ActivitySignupBinding((LinearLayout) rootView, alreadyUserId, emailEt, emailTil,
          isStudent, isTeacher, passwordEt, passwordTil, signupBtnId, textViewID);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
