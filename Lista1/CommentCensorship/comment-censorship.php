<?php

/**
 * Plugin Name: Comments censorship
 * Plugin URI: https://example.com/plugins/Comment Censorship/
 * Description: Censor unwanted words in comments to your posts.
 * Version: 1.0
 * Requires at least: 5.0
 * Requires PHP: 7.2
 * Author: Klaudia Błażyczek, Monika Galińska, Justyna Małuszyńska
 * Author URI: https://darksource.pl/
 * License: GPL v2 or later
 * License URI: https://www.gnu.org/licenses/gpl-2.0.html
 */

function debug_to_console($data)
{
    $output = $data;
    if (is_array($output))
        $output = implode(',', $output);

    echo "<script>console.log('Debug Objects: " . $output . "' );</script>";
}

function cc_admin_actions_register_menu()
{
    add_options_page("Comment Censorship", "Censor unwanted words", "manage_options", "cc", "cc_admin_page");
}

function cc_admin_page()
{
    global $_POST;
    if (isset($_POST['cc_add_word'])) {
        if ($_POST['cc_add_word'] == 'Y') {
            # jest już word w bazie
            if (get_option("cc_words")) {
                $new_word = $_POST['cc_new_word'];
                $words = get_option("cc_words");
                array_push($words, $new_word);
            } else {
                $new_word = $_POST['cc_new_word'];
                $words = array($new_word);
            }
            update_option('cc_words', $words);
            echo '<div class="notice notice-success isdismissible"><p>Word added.</p></div>';
        }
    }

    if (isset($_POST['cc_remove_words'])) {
        if ($_POST['cc_remove_words'] == 'Y') {
            $words = get_option('cc_words');
            $index_word_to_delete = $_POST['remove_index'];
            if (empty($index_word_to_delete)) {
                echo '<div class="notice notice-error isdismissible"><p>You did not select any words. </p></div>';
            } else {
                for ($i = 0; $i < count($index_word_to_delete); $i++) {
                    unset($words[$index_word_to_delete[intval($i)]]);
                }
                $words = array_values($words);
                update_option('cc_words', $words);
                echo '<div class="notice notice-success isdismissible"><p>Words deleted. </p></div>';
            }
        }
    }

    $words = get_option('cc_words');
?>
    <div class="wrap">
        <h1>Add word that you want to cesore</h1>
        <form name="cc_form_add" method="post">
            <input type="hidden" name="cc_add_word" value="Y">
            <p>Add word</p>
            <p><input type="text" minlength="2" placeholder="word to censore" name="cc_new_word" /></p>
            <p class="submit"><input class="submit-button" type="submit" value="Add"></p>
        </form>

        <?php
        if (!(empty($words))) {
        ?>
            <h1>Remove words that shouldn't be censored</h1>
            <form name="cc_form_remove" method="post">
                <input type="hidden" name="cc_remove_words" value="Y">
                <p>Remove words</p>
                <?php
                for ($i = 0; $i < count($words); $i++) {
                ?>
                    <div class="my-wrpper">
                        <input type="checkbox" id="<?php echo $i ?>" name="remove_index[]" value="<?php echo $i ?>" />
                        <span><?php echo $words[$i] ?></span>
                    </div>
                <?php
                }
                ?>

                <p class="submit"><input class="submit-button" type="submit" value="Remove"></p>
            </form>
        <?php
        }
        ?>

    </div>
<?php
}

function check_comment_for_violations($comment_text)
{
    $comment = $comment_text;
    $censored_words = get_option("cc_words");

    foreach ($censored_words as $word) {
        $replace_str = str_repeat('*', strlen($word));
        $comment = str_ireplace($word, $replace_str, $comment);
    }
    return $comment;
}

function censor_comment($comment)
{
    $comment = check_comment_for_violations($comment);

    return $comment;
}

add_action('comment_post', 'test_notify');
function test_notify($args)
{
    debug_to_console("jestem w dunkcji");
    $comment = get_comment($args);
    $message = $comment->comment_content;


    if (strcmp($message, check_comment_for_violations($message)) !== 0) {
        $mail = '254563@student.pwr.edu.pl';
        $subject = sprintf('New illegal comment by: %s', $comment->comment_author);
        wp_mail($mail, $subject, $message);
        debug_to_console($mail);
        debug_to_console($subject);
    }
}

function cc_register_styles()
{
    wp_register_style('cc_styles', plugins_url('/css/style.css', __FILE__));
    wp_enqueue_style('cc_styles');
}


# add_action('edit_comment', 'send_mail');
add_filter('comment_text', 'censor_comment');
add_action('admin_menu', 'cc_admin_actions_register_menu');
add_action('init', 'cc_register_styles');
