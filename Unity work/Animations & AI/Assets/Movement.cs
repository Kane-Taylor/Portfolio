using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Movement : MonoBehaviour
{
    private Animator animator;
    // Start is called before the first frame update
    void Start()
    {
        animator = GetComponent<Animator>();
       
    }

    // Update is called once per frame
    void Update()
    {
         //input for movement
        float x = Input.GetAxis("Horizontal");
        float z = Input.GetAxis("Vertical");

        


        if (x < 0)
        {
            animator.SetFloat("Turn", 1f);
        }
        else
        {
            if (x > 0)
                animator.SetFloat("Turn", -1f);
            else
            {
                animator.SetFloat("Turn", 0);
            }
        }

        float speed = 1f;
        if (Input.GetKey(KeyCode.LeftShift))
        {
            speed = 5f;
        }
        if (z > 0)
        {
            animator.SetFloat("Speed", speed);
            animator.SetFloat("Turn", 0);
        }
        else
        {
            animator.SetFloat("Speed", 0f);
        }
    }

    private void OnCollisionEnter(Collision collision)
    {
        animator.SetFloat("Speed", 0f);
    }
}
