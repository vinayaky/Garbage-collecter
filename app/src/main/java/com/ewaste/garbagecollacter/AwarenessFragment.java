package com.ewaste.garbagecollacter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class AwarenessFragment extends Fragment {
Button button1,button2,button3,button4,button5;
String content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_averness, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button1=view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content="In our modern world, technology has become an integral part of our daily lives. From smartphones and laptops to televisions and gaming consoles, we rely on electronic devices to stay connected, entertained, and productive. However, with the rapid pace of technological advancements, the problem of e-waste has emerged as a growing concern.\n\nE-waste, or electronic waste, refers to discarded electronic devices such as computers, televisions, cell phones, and other electronic gadgets. When these devices are no longer in use, they often end up in landfills or incinerators, leading to environmental pollution and health hazards.\n\nThe negative impact of e-waste on the environment cannot be overstated. Many electronic devices contain hazardous materials such as lead, cadmium, and mercury that can contaminate the soil, water, and air when disposed of improperly. These toxic substances can also pose a threat to human health, causing respiratory problems, neurological disorders, and other illnesses.\n\nMoreover, e-waste is a significant contributor to global carbon emissions. The manufacturing and disposal of electronic devices require large amounts of energy, leading to a significant carbon footprint. By recycling and reusing electronic devices, we can reduce the amount of energy needed to produce new ones, ultimately reducing our impact on the environment.\n\nFortunately, there are several ways to address the problem of e-waste. Recycling and reusing electronic devices is a crucial step in reducing the negative impact of e-waste on the environment. Many electronic devices can be refurbished and repurposed for use, reducing the need to manufacture new devices and ultimately reducing carbon emissions.\n\nIn addition to recycling and reusing, responsible e-waste disposal is also essential. Many cities and towns have e-waste collection centers that accept old electronic devices for proper disposal. These centers ensure that electronic devices are disposed of safely, preventing toxic substances from contaminating the environment.\n\nFurthermore, as consumers, we can make a difference by choosing to buy electronic devices that are designed for longevity and repairability. By opting for devices that can be repaired or upgraded, we can extend their lifespan and reduce the amount of e-waste generated.\n\nIn conclusion, e-waste is a growing problem that requires immediate attention. By taking steps to recycle and responsibly dispose of electronic devices, we can reduce our impact on the environment and promote sustainability. As individuals and communities, we must work together to address the problem of e-waste and create a more sustainable future for all.";
                Intent intent =new Intent(getContext(),AwarenessContent.class);
                intent.putExtra("content",content);
                startActivity(intent);
            }
        });
    }
}